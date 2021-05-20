package core;

import commands.Command;

import java.io.*;
import java.net.*;

public class Server {
    private DatagramSocket socket;

    public Server(String[] args) {
        if (args.length < 2) {
            System.out.println("Сервер не запущен, так как не указан порт!\n(число от 0 до 65535 должно быть передано вторым аргументом командной строки)");
        } else {
            try {
                socket = new DatagramSocket((int) Long.parseLong(args[1]));
                System.out.println("Сервер запущен на порте "+args[1]+"!");
            } catch (SocketException e) {
                System.out.println("Не удалось запустить сервер на порте "+args[1]+"!");
            } catch (NumberFormatException e) {
                System.out.println("Сервер не запущен, так как указан неправильный формат порта!\n(число от 0 до 65535 должно быть передано вторым аргументом командной строки)");
            }
        }
    }

    public void run() {
        int i = 1;
        while(true) {
            try {
                byte[] b = new byte[10000];
                DatagramPacket packet1 = new DatagramPacket(b, b.length);

                socket.receive(packet1);
                System.out.println("Подключен клиент "+i+"c IP "+packet1.getAddress());
                i++;

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
}