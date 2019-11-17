package sunrise.environment.collision;

import sunrise.entity.base.SVector;
import sunrise.entity.space.SShape;
import sunrise.entity.space.polygon.SPolygon;
import sunrise.entity.space.polygon.SSquare;

public class CollisionDetector {

    public boolean detectCollision(SShape s1, SShape s2) throws UnimplementedCollisionException {
        throw new UnimplementedCollisionException();
    }

    public boolean detectCollision(SPolygon s1, SPolygon s2) {
        //Big optimisation problem. I might fix it later (or never xd)
        return isOverlappingShadow(s1, s2) && isOverlappingShadow(s2, s1);
    }

    private boolean isOverlappingShadow(SPolygon s1, SPolygon s2) {
        SVector[] p1 = s1.getVertex();
        SVector[] p2 = s2.getVertex();

        //Creation des axes
        SVector[] axis = new SVector[p1.length];

        for(int i = 0; i < p1.length - 1; i++) {
            axis[i] = p1[i];
            axis[i].subtract(p1[i + 1]);
            axis[i].setY(-axis[i].getY());
        }

        axis[axis.length - 1] = p1[axis.length - 1];
        axis[axis.length - 1].subtract(p1[0]);
        axis[axis.length - 1].setY(-axis[axis.length - 1].getY());

        //Essai un des points les plus
        //SVector[][] axisExtremes1 = new SVector[axis.length][2];
        //SVector[][] axisExtremes2 = new SVector[axis.length][2];

        for(int i = 0; i < axis.length; i++) {
            double highest1 = SVector.dot(axis[i], p1[0]);
            double lowest1 = SVector.dot(axis[i], p1[0]);
            double highest2 = SVector.dot(axis[i], p2[0]);
            double lowest2 = SVector.dot(axis[i], p2[0]);

            SVector highestPoint1 = p1[0];
            SVector lowestPoint1 = p1[0];

            SVector highestPoint2 = p2[0];
            SVector lowestPoint2 = p2[0];

            for(int j = 1; j < p1.length; j++) {
                double candidate = SVector.dot(axis[i], p1[j]);

                if(candidate < lowest1) {
                    lowest1 = candidate;
                    lowestPoint1 = p1[j];
                }

                if(candidate > highest1){
                    highest1 = candidate;
                    highestPoint1 = p1[j];
                }
            }

            for(int j = 1; j < p2.length; j++) {
                double candidate = SVector.dot(axis[i], p2[j]);

                if(candidate < lowest2) {
                    lowest2 = candidate;
                    lowestPoint2 = p2[j];
                }

                if(candidate > highest2) {
                    highest2 = candidate;
                    highestPoint2 = p2[j];
                }
            }

            SVector projection1 = SVector.project(SVector.subtract(highestPoint1, lowestPoint1), axis[i]);
            SVector projection2 = SVector.project(SVector.subtract(highestPoint2, lowestPoint2), axis[i]);
            double projectionLength1 = projection1.normal();
            double projectionLength2 = projection2.normal();

            double longestProjection = -1;

            SVector lowLow = SVector.subtract(lowestPoint1, lowestPoint2);
            SVector lowHigh = SVector.subtract(lowestPoint1, highestPoint2);
            SVector highLow = SVector.subtract(highestPoint1, lowestPoint2);
            SVector highHigh = SVector.subtract(highestPoint1, highestPoint2);

            longestProjection = Math.max(SVector.project(lowLow, axis[i]).normal(), longestProjection);
            longestProjection = Math.max(SVector.project(lowHigh, axis[i]).normal(), longestProjection);
            longestProjection = Math.max(SVector.project(highLow, axis[i]).normal(), longestProjection);
            longestProjection = Math.max(SVector.project(highHigh, axis[i]).normal(), longestProjection);

            System.out.println(i + " - " + longestProjection + " - " + projectionLength1 + " - " + projectionLength2);

            if(longestProjection > projectionLength1 + projectionLength2)
                return false;
        }

        return true; //TODO
    }
}
