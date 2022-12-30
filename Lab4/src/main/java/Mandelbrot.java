import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator
{    /**Этот метод позволяет генератору фракталов указать, какая часть
     * комплексной плоскости наиболее интересна для фрактала.
     * Ему передаётся объект прямоугольника, и метод изменяет поля прямоугольника,
     * чтобы показать правильный начальный диапазон для фрактала.
     * Эта реализация устанавливает начальный диапазон в (-2 - 1.5i) - (1 + 1.5i)
     * или x = -2, y = -1,5, width = 3, height = 3.*/
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    /**Константа для количества максимальных итераций.*/
    public static final int MAX_ITERATIONS = 2000;

    /** Этот метод реализует итерационную функцию для фрактала Мандельброта.
     * Требуется два числа double для действительной и мнимой частей комплекса plane
     * и возвращаетcя количество итераций для соответствующей
     * координаты.*/
    public int numIterations(double x, double y)
    {
        int iteration = 0;
        double zreal = 0;
        double zimaginary = 0;

        /**
         * Вычисляем Zn = Zn-1 ^ 2 + c, где значения представляют собой комплексные числа,
         * представленные по zreal и zimaginary, Z0 = 0, а c - особая точка во фрактале,
         * который мы показываем (заданный x и y). Это повторяется
         * до Z ^ 2> 4 (абсолютное значение Z больше 2)
         * или максимум достигнуто количество итераций.*/
        while (iteration < MAX_ITERATIONS &&
                zreal * zreal + zimaginary * zimaginary < 4)
        {
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = 2 * zreal * zimaginary + y;
            zreal = zrealUpdated;
            zimaginary = zimaginaryUpdated;
            iteration += 1;
        }

        /** Если количество максимальных итераций достигнуто, возвращаем -1,
         * чтобы указать, что точка не вышла за границу.*/
        if (iteration == MAX_ITERATIONS)
        {
            return -1;
        }

        return iteration;
    }

    /** Возвращает название фрактала: «Мандельброт».*/
    public String toString() {
        return "Mandelbrot";
    }

}