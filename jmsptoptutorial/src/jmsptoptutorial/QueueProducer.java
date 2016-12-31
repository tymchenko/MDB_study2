package jmsptoptutorial;

import javax.jms.*;
import javax.naming.*;

public class QueueProducer {

	public static void main(String[] args) throws JMSException, NamingException {
		
		System.out.println("----Entering JMS Example Queueproducer----");
		Context context = QueueConsumer.getInitialContext();
		QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
		Queue queue = (Queue) context.lookup("/queue/jms_training");
		QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
		QueueSession queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		queueConnection.start();
		QueueProducer queueProducer = new QueueProducer();
		queueProducer.sendMessage("message 1 from QueueProducer.....", queueSession, queue);
		System.out.println("----Exiting JMS Example QueueProducer----");
	}

	private void sendMessage(String text, QueueSession queueSession, Queue queue) throws JMSException {
		
		QueueSender queueSender = queueSession.createSender(queue);
		TextMessage textMessage = queueSession.createTextMessage(text);
		queueSender.send(textMessage);
		queueSender.close();
	}

}
