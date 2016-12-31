package jmsptoptutorial;

import javax.jms.*;
import javax.naming.*;
import java.util.Properties;

public class QueueConsumer implements MessageListener {

	public static void main(String[] args) throws NamingException, JMSException {

        System.out.println("----Entring JMS Example QueueConsumer----");
        Context context = QueueConsumer.getInitialContext();
        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
        Queue queue = (Queue) context.lookup("/queue/jms_training");
        QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
        QueueSession queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

        QueueReceiver queueReceiver = queueSession.createReceiver(queue);
        queueReceiver.setMessageListener(new QueueConsumer());
        
        queueConnection.start();

        System.out.println("----Exiting JMS Example QueueConsumer----");
	}

    public void onMessage(Message message) {

        try{
            System.out.println("Incoming messages: " + ((TextMessage)message).getText());
        }catch(JMSException e){
            e.printStackTrace();
        }
    }

    public static Context getInitialContext() throws JMSException, NamingException {

        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        props.setProperty("java.naming,factory.url.pkgs", "org.jboss.naming");
        props.setProperty("java.naming.provider.url", "localhost:1099");

        Context context = new InitialContext(props);
        return context;
    }

}