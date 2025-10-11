package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.FollowTagModel;

public record FollowTagDto(
        int id,
        int tagId,
        int accountId,
        String nameTag
) {
    public static FollowTagDto of(FollowTagModel followTagModel){
        return new FollowTagDto(
                followTagModel.id(),
                followTagModel.tagId(),
                followTagModel.accountId(),
                followTagModel.nameTag()
        );
    }
}
