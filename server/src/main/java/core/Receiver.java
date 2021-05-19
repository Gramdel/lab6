package core;

import commands.Command;

import java.io.*;
import java.net.*;

public class Receiver {
    public static void receive() {
        try {
            DatagramSocket socket = new DatagramSocket(12700);
            byte[] b = new byte[10000];
            DatagramPacket packet1 = new DatagramPacket(b, b.length);
            socket.receive(packet1);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Command command = (Command) objectInputStream.readObject();
            System.out.println("Получение успешно!");

            try {
                String response = command.execute();
                b = response.getBytes();
                DatagramPacket packet2 = new DatagramPacket(b, b.length, packet1.getAddress(), packet1.getPort());
                socket.send(packet2);
                System.out.println("Отправка успешна!");
            } catch (IOException e) {
                System.out.println("Отправка не удалась!");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Получение не удалось!");
        }
    }
}