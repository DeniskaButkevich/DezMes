package dez.DezMes.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import dez.DezMes.domain.User;
import dez.DezMes.domain.Views;
import dez.DezMes.repo.UserDetailsRepo;
import dez.DezMes.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;
    private UserDetailsRepo userDetailsRepo;

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
}
