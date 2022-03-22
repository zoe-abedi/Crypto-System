import java.applet.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Play extends JApplet
{
  public void init()
  {
    String fileName = getParameter("clip");

    clip = getAudioClip(getDocumentBase(),fileName);

    button = new JButton("play");

    button.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
      if(e.getActionCommand().equals("play"))
      {
      clip.play();
      button.setText("stop");
      }
      else
      {
      clip.stop();
      button.setText("play");
      }
      }
      }
      );
      getContentPane().add(button);
 }
      AudioClip clip;
      JButton button;
}
