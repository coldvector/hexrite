package io.codevector.hexrite.repository.setting;

import io.codevector.hexrite.entity.setting.Setting;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class SettingRepository implements PanacheRepositoryBase<Setting, String> {

  private static final Logger LOG = Logger.getLogger(SettingRepository.class.getSimpleName());
}
