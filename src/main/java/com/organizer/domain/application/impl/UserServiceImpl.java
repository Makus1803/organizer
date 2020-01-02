package com.organizer.domain.application.impl;

import com.organizer.domain.application.UserService;
import com.organizer.domain.application.commands.RegistrationCommand;
import com.organizer.domain.model.user.RegistrationException;
import com.organizer.domain.model.user.User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
  @Override
  public void register(RegistrationCommand command) throws RegistrationException {

  }
}
