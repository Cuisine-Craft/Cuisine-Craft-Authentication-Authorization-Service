package fontys.sem3.school.business.impl;
import fontys.sem3.school.business.UserValidator;
import fontys.sem3.school.business.exception.UserNotFoundException;
import fontys.sem3.school.domain.Chat;
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

import java.time.LocalDateTime;
import java.util.Collections;
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
//        // Arrange
//        CreateChatRequest request = CreateChatRequest.builder()
//                .customerid(1L)
//                .sellerid(2L)
//                .build();
//
//        UserEntity mockCustomer = UserEntity.builder().id(1L).name("Customer").build();
//        UserEntity mockSeller = UserEntity.builder().id(2L).name("Seller").build();
//        ChatEntity existingChat = ChatEntity.builder()
//                .id(1L)
//                .customerid(mockCustomer)
//                .sellerid(mockSeller)
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));
//        when(userRepository.findById(2L)).thenReturn(Optional.of(mockSeller));
//        when(chatRepository.findByCustomerid_IdAndSellerid_Id(1L, 2L)).thenReturn(Optional.of(existingChat));
//        when(chatRepository.save(any(ChatEntity.class))).thenReturn(existingChat);
//
//        // Act
//        CreateChatResponse response = chatUseCase.createChat(request);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(1L, response.getId());
//        assertEquals(2L, response.getSellerid());
//        assertEquals(1L, response.getCustomerid());
//        assertEquals("Seller", response.getSellername());
//        assertEquals("Customer", response.getCustomername());
//
//        // Verify that userRepository.findById was called twice
//        verify(userRepository, times(2)).findById(anyLong());
//
//        // Verify that chatRepository.findByCustomerid_IdAndSellerid_Id was called once
//        verify(chatRepository, times(1)).findByCustomerid_IdAndSellerid_Id(1L, 2L);
//
//        // Verify that chatRepository.save was called once
//        verify(chatRepository, times(1)).save(any(ChatEntity.class));
    }
    @Test
    void testGetChatsbyCustomerid() {
        // Arrange
//        long customerid = 1L;
//        List<ChatEntity> mockChats = Collections.singletonList(
//                ChatEntity.builder()
//                        .id(1L)
//                        .customerid(UserEntity.builder().id(customerid).build())
//                        .sellerid(UserEntity.builder().id(2L).build())
//                        .updatedAt(LocalDateTime.now())
//                        .build()
//        );
//
//        when(chatRepository.findByCustomerid_Id(customerid)).thenReturn(mockChats);
//
//        // Act
//        GetChatsResponse response = chatUseCase.getChatsbyCustomerid(customerid);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(1, response.getChats().size());
//
//        Chat chat = response.getChats().get(0);
//        assertEquals(1L, chat.getId());
//        assertEquals(2L, chat.getSellerid().getId());
    }




}
