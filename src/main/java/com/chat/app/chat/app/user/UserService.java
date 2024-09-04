package com.chat.app.chat.app.user;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  public void saveUser(User user) {
    user.setStatus(Status.ONLINE);
    repository.save(user);
  }

  public void disconnect(User user) {
    User savedUser = repository.findById(user.getNickName()).orElse(null);

    if (savedUser != null)
      savedUser.setStatus(Status.OFFLINE);
    repository.save(savedUser);
  }

  public List<User> findConnectedUsers() {
    return repository.findByStatus(Status.ONLINE);

  }
}
