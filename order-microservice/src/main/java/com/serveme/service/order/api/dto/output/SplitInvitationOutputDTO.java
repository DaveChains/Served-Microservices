package com.serveme.service.order.api.dto.output;

import com.serveme.service.order.external.dto.output.UserExDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Davids-iMac on 01/12/15.
 */
public class SplitInvitationOutputDTO implements Serializable {


    private List<String> notFound;

    private List<UserExDTO> withActiveOrder;

    private List<UserExDTO> invited;

    public List<String> getNotFound() {
        return notFound;
    }

    public void setNotFound(List<String> notFound) {
        this.notFound = notFound;
    }

    public List<UserExDTO> getWithActiveOrder() {
        return withActiveOrder;
    }

    public void setWithActiveOrder(List<UserExDTO> withActiveOrder) {
        this.withActiveOrder = withActiveOrder;
    }

    public List<UserExDTO> getInvited() {
        return invited;
    }

    public void setInvited(List<UserExDTO> invited) {
        this.invited = invited;
    }
}
