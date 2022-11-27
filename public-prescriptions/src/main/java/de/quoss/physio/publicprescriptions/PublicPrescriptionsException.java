package de.quoss.physio.publicprescriptions;

public class PublicPrescriptionsException extends RuntimeException {
    
    public PublicPrescriptionsException(final String s) {
        super(s);
    }

    public PublicPrescriptionsException(final Throwable t) {
        super(t);
    }

    public PublicPrescriptionsException(final String s, final Throwable t) {
        super(s, t);
    }

}
