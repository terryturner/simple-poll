package com.simple.poll.helper;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simple.poll.config.security.IAuthenticationFacade;
import com.simple.poll.database.entity.BaseEntity;
import com.simple.poll.model.JwtUserDetails;

@Component
public class BaseEntityHelper {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public void preBuildSetting(BaseEntity entity) {
        preBuildSetting(entity, "system");
    }
    
    public void preBuildSetting(BaseEntity entity, String defaulUserName) {
        Optional<JwtUserDetails> optionalUserDetail = authenticationFacade.getOptionalJwtUserDetail();
        // step: check the API has token or not
        if (optionalUserDetail.isPresent()) {
            fillCreateOrUpdateColumn(entity, optionalUserDetail.get().getId().toString());
        } else {
            fillCreateOrUpdateColumn(entity, defaulUserName);
        }

        if (entity.getRevision() == null)
            entity.setRevision(0);
        else
            entity.setRevision(entity.getRevision());
    }
    
    private void fillCreateOrUpdateColumn(BaseEntity entity, String defaulUserName) {
        // fill create or update columns of the entity
        if (entity.getCreateDate() != null) {
            entity.setUpdateUser(defaulUserName);
            entity.setUpdateDate(LocalDateTime.now());
        } else {
            entity.setCreateUser(defaulUserName);
            entity.setCreateDate(LocalDateTime.now());
        }
    } 
}
