import java.util.*;

public class ReservationNode implements Comparable<ReservationNode> {
    private int patronId;
    private int priorityNumber;
    private Date timeOfReservation;

    public ReservationNode(int patronId, int priorityNumber, Date timeOfReservation) {
        this.patronId = patronId;
        this.priorityNumber = priorityNumber;
        this.timeOfReservation = timeOfReservation;
    }

    public ReservationNode() {

    }

    public int getPatronId() {
        return patronId;
    }

    public int getPriorityNumber() {
        return priorityNumber;
    }

    public Date getTimeOfReservation() {
        return timeOfReservation;
    }

    @Override
    public String toString() {
        return "(" + patronId + ", " + priorityNumber + ", " + timeOfReservation + ")";
    }

    @Override
    public int compareTo(ReservationNode o) {
        if (this.priorityNumber != o.priorityNumber)
            return this.priorityNumber - o.priorityNumber;
        else
            return this.timeOfReservation.compareTo(o.timeOfReservation);
    }

}
