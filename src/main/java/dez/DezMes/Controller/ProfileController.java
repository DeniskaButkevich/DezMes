package dez.DezMes.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import dez.DezMes.domain.User;
import dez.DezMes.domain.UserSubscription;
import dez.DezMes.domain.Views;
import dez.DezMes.repo.UserDetailsRepo;
import dez.DezMes.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;
    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public ProfileController(ProfileService profileService, UserDetailsRepo userDetailsRepo) {
        this.profileService = profileService;
        this.userDetailsRepo = userDetailsRepo;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") String id) {
        User user = userDetailsRepo.findById(id).get();
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(
            @AuthenticationPrincipal User subscriber,
            @PathVariable("channelId") String id
    ) {
        User channel = userDetailsRepo.findById(id).get();
        if (subscriber.equals(channel)) {
            return channel;
        } else {
            return profileService.changeSubscription(channel, subscriber);
        }
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdName.class)
    public List<UserSubscription> subscribers(
            @PathVariable("channelId") String channel_id
    ) {
        User channel = userDetailsRepo.findById(channel_id).get();
        return profileService.getSubscribers(channel);
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.IdName.class)
    public UserSubscription changeSubscriptionStatus(
            @AuthenticationPrincipal User channel,
            @PathVariable("subscriberId") String subscriber_id
    ) {
        User subscriber = userDetailsRepo.findById(subscriber_id).get();
        return profileService.changeSubscriptionStatus(channel, subscriber);
    }
}
