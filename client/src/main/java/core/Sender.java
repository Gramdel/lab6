package core;

import commands.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

public class Sender {
    public static void send(Command command) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
            byte[] b = byteArrayOutputStream.toByteArray();

            SocketAddress address = new InetSocketAddress("127.0.0.1",12700);
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet1 = new DatagramPacket(b, b.length, address);
            socket.send(packet1);
            System.out.println("Отправка успешна!");

            try {
                b = new byte[10000];
                DatagramPacket packet2 = new DatagramPacket(b, b.length);
                socket.receive(packet2);
                System.out.println("Получение успешно!");
                System.out.println(new String(b));
            } catch (IOException e) {
                System.out.println("Получение не удалось!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Отправка не удалась!");
        }
    }
}