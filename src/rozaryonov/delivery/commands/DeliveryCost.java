package rozaryonov.delivery.commands;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.LocalityDao;
import rozaryonov.delivery.dao.impl.PropertyDao;
import rozaryonov.delivery.dao.impl.TariffDao;
import rozaryonov.delivery.entities.Locality;
import rozaryonov.delivery.exceptions.DaoException;
import rozaryonov.delivery.services.PathFinder;

public class DeliveryCost implements ActionCommand {
	private Logger logger = LogManager.getLogger(DeliveryCost.class.getName());
	@Override
	public String execute(HttpServletRequest request) {
		
		String redirection=null;
		redirection=request.getParameter("goTo");
		if (redirection==null) redirection="view/delivery_cost.jsp";
		
		HttpSession session = request.getSession(true);
		long departureId = Long.parseLong(request.getParameter("departure"));
		long arrivalId = Long.parseLong(request.getParameter("arrival"));
		int length = Integer.parseInt(request.getParameter("length"));
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		double weight = Integer.parseInt(request.getParameter("weight"));
		String locStrStr = (String) session.getAttribute("locale");
		String[] locPart = locStrStr.split("_");
		Locale locale = new Locale(locPart[0], locPart[1]);
		NumberFormat doubleFormat = NumberFormat.getInstance(locale);
		doubleFormat.setMaximumFractionDigits(2);
		NumberFormat intFormat = NumberFormat.getInstance();
		intFormat.setMaximumFractionDigits(0);
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("uk", "UA"));
		
		Connection connection = DeliveryConnectionPool.getConnection(); 
		PropertyDao propDao = new PropertyDao(connection);
		long logisticConfigId = Long.parseLong(propDao.findById("currentLogisticConfigId").orElseThrow(()->new DaoException("Can't find currentLogisticConfigId")).getValue());
		PathFinder pf = (PathFinder) session.getAttribute("pathfinder");
		if (pf==null) {
			session.removeAttribute("pathfinder");
			pf = new PathFinder(connection, logisticConfigId);
			session.setAttribute("pathfinder", pf);
		}
		long tariffId = Long.parseLong(propDao.findById("currentTariffId").orElseThrow(()->new DaoException("Can't find current tariff id")).getValue());
		
		try {
			String route = pf.showShortestPath(departureId, arrivalId);
			double distance = pf.calcMinDistance(departureId, arrivalId);

			TariffDao tarDao = new TariffDao(connection);
			double truckVelocity = tarDao.findById(tariffId).orElseThrow(()->new DaoException("Can't find current tariffs")).getTruckVelocity();
			double dencity = tarDao.findById(tariffId).orElseThrow(()->new DaoException("Can't find current tariffs")).getDensity();
			double paperwork = tarDao.findById(tariffId).orElseThrow(()->new DaoException("Can't find current tariffs")).getPaperwork();
			double targetReceiptDist = tarDao.findById(tariffId).orElseThrow(()->new DaoException("Can't find current tariffs")).getTargetedReceipt();
			double targetDeliveryDist = tarDao.findById(tariffId).orElseThrow(()->new DaoException("Can't find current tariffs")).getTargetedDelivery();
			double shippingRate = tarDao.findById(tariffId).orElseThrow(()->new DaoException("Can't find current tariffs")).getShippingRate();
			double insuranceWorth = tarDao.findById(tariffId).orElseThrow(()->new DaoException("Can't find current tariffs")).getInsuranceWorth();
			double insuranceRate = tarDao.findById(tariffId).orElseThrow(()->new DaoException("Can't find current tariffs")).getInsuranceRate();
			
			Duration duration = Duration.ofHours((long) (distance / truckVelocity + 48));
			long dur = duration.toDays();
			double volumeWeight = length * width * height / 1000 * dencity;
			double volume = length * width * height / 1000;
			double usedWeight = Double.max(weight, volumeWeight);
			double targetReceiptCost = targetReceiptDist * usedWeight * shippingRate / 100;
			double interCityCost = distance * usedWeight * shippingRate /100;
			double targetDeliveryCost = targetDeliveryDist * usedWeight * shippingRate / 100;
			double insuranceCost = usedWeight * insuranceWorth * insuranceRate;
			double total = paperwork + targetReceiptCost + interCityCost + targetDeliveryCost + insuranceCost;
			
			LocalityDao locDao = new LocalityDao(connection);
			Locality loadLocality = locDao.findById(departureId).orElseThrow(()-> new DaoException("No Locality found with id=" + departureId));
			Locality unloadLocality = locDao.findById(arrivalId).orElseThrow(()-> new DaoException("No Locality found with id=" + departureId));
			
			session.setAttribute("loadLocality", loadLocality);
			session.setAttribute("unloadLocality", unloadLocality);
			session.setAttribute("route", route);
			session.setAttribute("distanceD", distance);
			session.setAttribute("weightD", weight);
			session.setAttribute("volumeD", volume);
			session.setAttribute("totalD", total);
			session.setAttribute("duration", intFormat.format(dur));
			session.setAttribute("weight", doubleFormat.format(weight));
			session.setAttribute("paperwork", doubleFormat.format(paperwork));
			session.setAttribute("volumeWeight", doubleFormat.format(volumeWeight));
			session.setAttribute("volume", doubleFormat.format(volume));
			session.setAttribute("usedWeight", doubleFormat.format(usedWeight));
			session.setAttribute("targetReceipt", doubleFormat.format(targetReceiptCost));
			session.setAttribute("interCityCost", doubleFormat.format(interCityCost));
			session.setAttribute("targetDelivery", doubleFormat.format(targetDeliveryCost));
			session.setAttribute("insuranceWorth", doubleFormat.format(insuranceWorth));
			session.setAttribute("insuranceRate", doubleFormat.format(insuranceRate));
			session.setAttribute("insuranceCost", doubleFormat.format(insuranceCost));
			session.setAttribute("totalMoney", currencyFormat.format(total));
			session.setAttribute("shippingRate", doubleFormat.format(shippingRate));
			session.setAttribute("targetReceiptDist", doubleFormat.format(targetReceiptDist));
			session.setAttribute("targetDeliveryDist", doubleFormat.format(targetDeliveryDist));
			session.setAttribute("date", LocalDateTime.now().plusDays(dur));
			
		
		} catch (ClassNotFoundException | IOException e) {
			logger.warn(e.getMessage());
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
		
		return redirection;
	}

}
