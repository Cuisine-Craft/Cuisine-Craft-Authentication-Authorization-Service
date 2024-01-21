package fontys.sem3.school.business.impl;

import fontys.sem3.school.business.UserValidator;
import fontys.sem3.school.business.exception.UserNotFoundException;
import fontys.sem3.school.domain.*;
import fontys.sem3.school.persistence.ChatRepository;
import fontys.sem3.school.persistence.MessageRepository;
import fontys.sem3.school.persistence.UserRepository;
import fontys.sem3.school.persistence.entity.ChatEntity;
import fontys.sem3.school.persistence.entity.MessageEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserValidator messageValidator;

    @InjectMocks
    private MessageUseCaseImpl messageUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageUseCase = new MessageUseCaseImpl(
                userRepository,
                chatRepository,
                messageRepository,
                messageValidator
        );
    }

    @Test
    void testCreateMessage() {
        // Arrange
        CreateMessageRequest request = CreateMessageRequest.builder()
                .senderid(1L)
                .chatid(1L)
                .content("Hello, world!")
                .build();

        UserEntity mockUser = UserEntity.builder().id(1L).build();
        ChatEntity mockChat = ChatEntity.builder().id(1L).build();
        MessageEntity savedMessage = MessageEntity.builder().id(1L).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(chatRepository.findById(1L)).thenReturn(Optional.of(mockChat));
        when(messageRepository.save(any(MessageEntity.class))).thenReturn(savedMessage);

        // Act
        CreateMessageResponse response = messageUseCase.createMessage(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());

        // Verify that userRepository.findById and chatRepository.findById were called once each
        verify(userRepository, times(1)).findById(1L);
        verify(chatRepository, times(1)).findById(1L);

        // Verify that messageRepository.save was called once
        verify(messageRepository, times(1)).save(any(MessageEntity.class));
    }

    @Test
    void testGetMessagesByChatId() {
        // Arrange
        long chatId = 1L;
        when(messageRepository.findByChatid_Id(chatId)).thenReturn(Collections.emptyList());

        // Act
        GetMessagesResponse response = messageUseCase.getMessagesbyChatid(chatId);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getMessages());
        assertTrue(response.getMessages().isEmpty());

        // Verify that messageRepository.findByChatid_Id was called once
        verify(messageRepository, times(1)).findByChatid_Id(chatId);
    }

    @Test
    void testCreateMessageWithInvalidUser() {
        // Arrange
        CreateMessageRequest request = CreateMessageRequest.builder()
                .senderid(1L)
                .chatid(1L)
                .content("Hello, world!")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> messageUseCase.createMessage(request));

        // Verify that userRepository.findById was called once
        // Verify that chatRepository.findById was not called
        verify(chatRepository, times(0)).findById(anyLong());

        // Verify that messageRepository.save was not called
        verify(messageRepository, times(0)).save(any(MessageEntity.class));

        // Assert the exception status and reason
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("USER_NOT_FOUND", exception.getReason());
    }





}