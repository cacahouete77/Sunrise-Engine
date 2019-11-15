package sunrise;

import sunrise.entity.base.SVector;
import sunrise.entity.space.polygon.SSquare;

public class STest {
    public static void main(String[] args) {
        SSquare square = new SSquare(new SVector(0, 0), new SVector(1, 1), 0) {
            @Override
            public void start() {

            }

            @Override
            public void update(long time) {

            }
        };

        SVector[] vertex = square.getVertex();

        for(SVector vector : vertex)
            System.out.println(vector);
    }
}
