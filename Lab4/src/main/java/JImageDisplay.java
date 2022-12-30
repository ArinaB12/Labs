import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

class JImageDisplay extends JComponent
{
    private BufferedImage image;
    /**Управляет изображением, содержимое которого можно записать**/
    public BufferedImage getImage()
    {
        return image;
    }

    /**Конструктор принимает целочисленные значения ширины и высоты, и инициализирует объект
     * BufferedImage новым изображением с этой шириной и высотой типа изображения TYPE_INT_RGB.**/
    public JImageDisplay(int width, int height) {
        image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        /**Вызваем метод setPreferredSize() родительского класса с указанной шириной и высотой.*/
        Dimension imageDimension = new Dimension(width, height);
        super.setPreferredSize(imageDimension);
    }

    /**Реализация суперкласса paintComponent(g) вызывает так, что границы и
     * черты нарисованы правильно. Затем изображение втягивается в компонент.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    /**Устанавливает все пиксели изображения в черный цвет.*/
    public void clearImage()
    {
        int[] blankArray = new int[getWidth() * getHeight()];
        image.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);
    }

    /**Устанавливает пиксель определенного цвета.*/
    public void drawPixel(int x, int y, int rgbColor)
    {
        image.setRGB(x, y, rgbColor);
    }
}