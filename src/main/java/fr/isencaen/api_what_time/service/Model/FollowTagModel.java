package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.FollowTag;

public record FollowTagModel(
        int id,
        int tagId,
        int accountId,
        String nameTag
) {
    public static FollowTagModel of(FollowTag followTag){
        return new FollowTagModel(
                followTag.getId(),
                followTag.getTag().getId(),
                followTag.getAccount().getId(),
                followTag.getTag().getName()
        );
    }
}
