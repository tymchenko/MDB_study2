package jmsptoptutorial;

import javax.jms.*;
import javax.naming.*;
import java.util.Properties;

public class QueueConsumer2 implements MessageListener {

	public static void main(String[] args) throws NamingException, JMSException {

        System.out.println("----Entring JMS Example QueueConsumer 2----");
        Context context = QueueConsumer2.getInitialContext();
        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
        Queue queue = (Queue) context.lookup("/queue/jms_training");
        QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
        QueueSession queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

        QueueReceiver queueReceiver = queueSession.createReceiver(queue);
        queueReceiver.setMessageListener(new QueueConsumer2());
        
        queueConnection.start();

        System.out.println("----Exiting JMS Example QueueConsumer 2----");
	}

    public void onMessage(Message message) {

        try{
            System.out.println("Incoming messages: to QueueConsumer 2" + ((TextMessage)message).getText());
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