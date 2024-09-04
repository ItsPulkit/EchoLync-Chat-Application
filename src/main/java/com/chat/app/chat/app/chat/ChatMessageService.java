package com.chat.app.chat.app.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chat.app.chat.app.chatroom.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

  private final ChatMessageRepository repository;
  private final ChatRoomService chatRoomService;

  public ChatMessage save(ChatMessage chatMessage) {
    var chatId = chatRoomService
        .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
        .orElseThrow(() -> new RuntimeException("Chatroom ID not found"));
    chatMessage.setChatId(chatId);
    repository.save(chatMessage);
    return chatMessage;
  }

  public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
    var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
    return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
  }

}
