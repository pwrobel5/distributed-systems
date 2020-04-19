package rabbitmq.model;

import java.io.*;

public class Message implements Serializable {
    private final String sender;
    private final String messageBody;
    private final MessageType messageType;

    public Message(String sender, String messageBody, MessageType messageType) {
        this.sender = sender;
        this.messageBody = messageBody;
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(this);
            out.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Message fromByteArray(byte[] array) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(array);
        ObjectInput input = null;
        try {
            input = new ObjectInputStream(byteArrayInputStream);
            Object object = input.readObject();
            if (object instanceof Message) {
                return (Message) object;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "From: " + sender + "\nBody: " + messageBody;
    }
}
