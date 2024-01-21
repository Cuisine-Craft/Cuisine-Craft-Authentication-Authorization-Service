package fontys.sem3.school.business.impl;
import fontys.sem3.school.business.UserValidator;
import fontys.sem3.school.business.exception.UserNotFoundException;
import fontys.sem3.school.domain.CreateChatRequest;
import fontys.sem3.school.domain.CreateChatResponse;
import fontys.sem3.school.domain.GetChatsResponse;
import fontys.sem3.school.persistence.ChatRepository;
import fontys.sem3.school.persistence.UserRepository;
import fontys.sem3.school.persistence.entity.ChatEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatUseCaseImplTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private ChatUseCaseImpl chatUseCase;
    @BeforeEach
    void setUp() {
        // Initialize mocks and inject them into the tested class
        MockitoAnnotations.openMocks(this);

        // Reset mocks to clear any interactions from previous tests
        reset(chatRepository, userRepository, userValidator);
    }
    @Test
    void testCreateChat() {
        // Arrange

    }





}
