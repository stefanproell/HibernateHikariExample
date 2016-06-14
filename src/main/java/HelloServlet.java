/**
 * Created by stefan on 13.06.16.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


public class HelloServlet extends HttpServlet {
    public void doGet (HttpServletRequest req,
                       HttpServletResponse res)
            throws ServletException, IOException
    {
        PrintWriter out = res.getWriter();
        addRandomNumber(req);



        out.println("Hello, world Stefan...!");
        out.println("There are " + countNumbers(req) + " random numbers");
        out.close();
    }

    private void addRandomNumber(HttpServletRequest request){
        SessionFactory sessionFactory = (SessionFactory) request.getServletContext().getAttribute("SessionFactory");

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        RandomNumberPOJO randomNumber = new RandomNumberPOJO();
        Random rand = new Random();
        int randomInteger = 1 + rand.nextInt((999) + 1);


        randomNumber.setRandomNumber(randomInteger);
        session.save(randomNumber);
        tx.commit();
        session.close();



    }

    private int countNumbers(HttpServletRequest request){
        SessionFactory sessionFactory = (SessionFactory) request.getServletContext().getAttribute("SessionFactory");
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();


        Query query = session.createQuery(
                "SELECT COUNT(id) FROM RandomNumberPOJO");

        String count = session.createQuery("SELECT COUNT(id) FROM RandomNumberPOJO").uniqueResult().toString();

        int rowCount = Integer.parseInt(count);

        tx.commit();
        session.close();
        return rowCount;
    }
}