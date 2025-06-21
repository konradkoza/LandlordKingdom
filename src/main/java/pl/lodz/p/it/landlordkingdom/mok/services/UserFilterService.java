package pl.lodz.p.it.landlordkingdom.mok.services;

import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.model.UserFilter;

public interface UserFilterService {
    void createOrUpdate(UserFilter userFilter);
    UserFilter getFilterForCurrentUser() throws NotFoundException;
}
