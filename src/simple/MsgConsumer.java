package simple;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MsgConsumer {

	public void run() {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session = null;
		Destination destination;
		MessageConsumer consumer = null;
		Message message;
		boolean useTransaction = false;
		
		try{
			connectionFactory = new ActiveMQConnectionFactory();
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(useTransaction, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("TEST.QUEUE");
			consumer = session.createConsumer(destination);
			message = consumer.receive(1000);
			System.out.println("Received message: " + message);
			System.out.println("msg text:"+((TextMessage)message).getText());
		}catch(JMSException jmsEx){
			jmsEx.printStackTrace();
		}finally{
			try {
				consumer.close();
				session.close();
				connection.close();
			}catch(JMSException jmsEx){
				jmsEx.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		MsgConsumer consumer = new MsgConsumer();
		consumer.run();
	}

}
