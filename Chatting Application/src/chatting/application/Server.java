package chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;


public class Server implements ActionListener{
    
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    
    Server() {
        
        f.setLayout(null);
        
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0,500,70);
        p1.setLayout(null);
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icon/back.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(10, 10, 25, 50);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("Icon/Miku.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(15, 10, 100, 50);
        p1.add(profile);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("Icon/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel call = new JLabel(i9);
        call.setBounds(330, 10, 60, 50);
        p1.add(call);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("Icon/call.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel video = new JLabel(i12);
        video.setBounds(380, 10, 60, 50);
        p1.add(video);
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("Icon/3.png"));
        Image i14 = i13.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel dots = new JLabel(i15);
        dots.setBounds(430, 10, 40, 50);
        p1.add(dots);
        
        JLabel name = new JLabel("Miku Kumar");
        name.setBounds(100,10,150,50);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Raleway", Font.BOLD, 20));
        p1.add(name);
        
        JLabel status = new JLabel("Active Now");
        status.setBounds(100,30,150,50);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(status);
        
        a1 = new JPanel();
        a1.setBounds(5, 75, 490, 625);
        f.add(a1);
        
        text = new JTextField();
        text.setBounds(5, 705, 350, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(370, 705, 125, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(send);
        
        f.setSize(500,750);
        f.setLocation(200,0);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        
        f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        try {
            String out = text.getText();        //recive text in textField You Have enter

            JLabel output = new JLabel(out);

            JPanel p2 = formateLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static JPanel formateLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">"+out+"</html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 211));          //message background
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
    }
    
    public static void main(String args[]) {
        new Server();
        
        try{
            ServerSocket skt = new ServerSocket(8080);
            while(true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true) {
                    String msg = din.readUTF();
                    JPanel panel = formateLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
