///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.errors.IllegalLoginOrPassword;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.PersonDisplay;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.SessionHolder;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.register.AuthRegister;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.dao.AuthDao;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.model.PersonLogin;
import kz.greetgo.security.password.PasswordEncoder;
import kz.greetgo.security.session.SessionIdentity;
import kz.greetgo.security.session.SessionService;

///MODIFY replace sandbox {PROJECT_NAME}
import static kz.greetgo.sandbox.controller.util.FilterUtil.skipNulls;

@Bean
public class AuthRegisterImpl implements AuthRegister {

  public BeanGetter<AuthDao> authDao;

  public BeanGetter<PasswordEncoder> passwordEncoder;

  public BeanGetter<SessionService> sessionService;

  @Override
  public SessionIdentity login(String username, String password) {

    PersonLogin personLogin = authDao.get().selectByUsername(username);
    if (personLogin == null) {
      throw new IllegalLoginOrPassword();
    }

    if (!passwordEncoder.get().verify(password, personLogin.encodedPassword)) {
      throw new IllegalLoginOrPassword();
    }

    SessionHolder sessionHolder = new SessionHolder(personLogin.id, null);

    return sessionService.get().createSession(sessionHolder);
  }

  private final ThreadLocal<SessionHolder> sessionDot = new ThreadLocal<>();

  @Override
  public void resetThreadLocalAndVerifySession(String sessionId, String token) {
    sessionDot.set(null);

    if (!sessionService.get().verifyId(sessionId)) {
      return;
    }

    if (!sessionService.get().verifyToken(sessionId, token)) {
      return;
    }

    sessionDot.set(sessionService.get().getSessionData(sessionId));
  }

  @Override
  public SessionHolder getSession() {
    return sessionDot.get();
  }

  @Override
  public PersonDisplay displayPerson(String personId) {
    PersonDisplay ret = authDao.get().loadDisplayPerson(personId);

    if (ret == null) {
      throw new NullPointerException("No person with id = " + personId);
    }

    ret.cans = skipNulls(authDao.get().loadCans(personId));

    return ret;
  }

  @Override
  public void deleteSession(String sessionId) {
    sessionService.get().removeSession(sessionId);
  }
}