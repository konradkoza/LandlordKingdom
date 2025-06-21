package pl.lodz.p.it.landlordkingdom.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.lodz.p.it.landlordkingdom.config.ToolConfig;
import pl.lodz.p.it.landlordkingdom.mok.repositories.UserRepository;
import pl.lodz.p.it.landlordkingdom.mok.services.UserService;

@ActiveProfiles("unit")
@SpringJUnitConfig({ToolConfig.class, MockConfig.class})
public class UnitExampleTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @Test
//    public void BlockUser_UserIsBlocked_ThrowException_Test() {
//        UUID userId = UUID.randomUUID();
//        User user = new User();
//        user.setBlocked(true);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//        user.setBlocked(true);
//        assertThrows(UserAlreadyBlockedException.class, () -> userService.blockUser(userId));
//    }
}
