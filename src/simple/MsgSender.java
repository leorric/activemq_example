package simple;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MsgSender {
	
	
	public void run() {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session = null;
		Destination destination;
		MessageProducer producer = null;
		Message message;
		boolean useTransaction = false;
		
		try{
			connectionFactory = new ActiveMQConnectionFactory();
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("TEST.QUEUE");
			producer = session.createProducer(destination);
			message = session.createTextMessage("i'm fine");
			producer.send(message);
			System.out.println("msg has been sent");
		}catch(JMSException jmsEx){
			jmsEx.printStackTrace();
		}finally{
			try {
				producer.close();
				session.close();
				connection.close();
			}catch(JMSException jmsEx){
				jmsEx.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		MsgSender producer = new MsgSender();
		producer.run();
	}
	
	
}
