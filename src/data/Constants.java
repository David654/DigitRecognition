package data;

import java.awt.*;

public class Constants
{
    public static final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    public static int width = d.width / 2;
    public static int height = d.height / 2;

    public static int tileNum = 28;
    public static int tileSize = width / 2 / tileNum;

    public static final String title = "Digit Recognition";

    public static final Color bgColor = Color.black;
    public static final Color fgColor = Color.white;
    public static final Font font = new Font("Calibri", Font.PLAIN, 20);
}