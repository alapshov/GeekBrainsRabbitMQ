import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class BlogSenderApp {
    private static final String EXCHANGE_NAME = "blog_exchanger";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Scanner in = new Scanner(System.in);
        System.out.println("Введите тему и название блога: ");
        String console = in.nextLine();

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            String theme = console.split(" ")[0];
            String message = console.substring(theme.length() + 1, console.length());
            channel.basicPublish(EXCHANGE_NAME, theme, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }

    }
}
