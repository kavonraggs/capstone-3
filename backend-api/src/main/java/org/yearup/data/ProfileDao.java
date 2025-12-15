package org.yearup.data;


import org.springframework.web.bind.annotation.RequestBody;
import org.yearup.models.Profile;

public interface ProfileDao {
    Profile create(Profile profile);
    Profile getByUserId(int userId);
    Profile updateProfile(Profile profile);

}
