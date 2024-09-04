package com.chat.app.chat.app.chatroom;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;

  public Optional<String> getChatRoomId(String sender, String recipient, boolean createChatIdIfNotExist) {

    return chatRoomRepository.findBySenderIdAndRecipientId(sender, recipient)
        .map(ChatRoom::getChatId).or(() -> {

          if (createChatIdIfNotExist) {
            String newchatid = createChatId(sender, recipient);
            return Optional.of(newchatid);
          } else {
            return Optional.empty();
          }
        });

  }

  private String createChatId(String senderId, String recipientId) {
    var chatId = String.format("%s_%s", senderId, recipientId);

    ChatRoom senderRecipient = ChatRoom
        .builder()
        .chatId(chatId)
        .senderId(senderId)
        .recipientId(recipientId)
        .build();

    ChatRoom recipientSender = ChatRoom
        .builder()
        .chatId(chatId)
        .senderId(recipientId)
        .recipientId(senderId)
        .build();

    chatRoomRepository.save(senderRecipient);
    chatRoomRepository.save(recipientSender);

    return chatId;
  }

}
