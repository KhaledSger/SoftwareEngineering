package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ClincEntity;

public class ClinicHandler {
    private ClincEntity clinic;

    public ClinicHandler(ClincEntity Clinc) {
        this.clinic = Clinc;
    }

    public ClincEntity getClinic() {
        return clinic;
    }
}
