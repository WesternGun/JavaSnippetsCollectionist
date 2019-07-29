package io.westerngun.threading;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Working example of synchonous, competitive writing to the same file.
 * @author WesternGun
 *
 */
class ThreadCompete implements Runnable {
    private FileWriter writer;
    private int status;
    private int counter;
    private boolean stop;
    private String name;


    public ThreadCompete(String name) {
        this.name = name;
        status = 0;
        stop = false;
        // just open the file without appending, to clear content
        try {
            writer = new FileWriter(new File("test.txt"), true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        for (int i=0; i<10; i++) {
            new Thread(new ThreadCompete("Thread" + i)).start();
        }
    }

    private int generateRandom(int range) {
        return (int) (Math.random() * range);
    }

    private String indentWithName(int name) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<name; i++) {
            sb.append("    ");
        }
        return sb.toString();
    }
    @Override
    public void run() {
        while (!stop) {
            try {
                writer = new FileWriter(new File("test.txt"), true);
                if (status == 0) {
                    writer.write(indentWithName(Integer.parseInt(name.substring(6, 7))) + this.name + ": Begin: " + counter);
                    Thread.sleep(generateRandom(1000));
                    writer.write(System.lineSeparator());
                    status ++;
                } else if (status == 1) {
                    writer.write(this.name + ": Now we have " + counter + " books!");
                    Thread.sleep(generateRandom(1000));
                    writer.write(System.lineSeparator());
                    counter++;
                    if (counter > 8) {
                        status = 2;
                    }

                } else if (status == 2) {
                    writer.write(this.name + ": End. " + counter);
                    Thread.sleep(generateRandom(1000));
                    writer.write(System.lineSeparator());
                    stop = true;
                }
                writer.flush();
                writer.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}