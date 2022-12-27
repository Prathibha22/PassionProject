import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/student">
        <Translate contentKey="global.menu.entities.student" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/studentUser">
        StudentUser
        {/*               <Translate contentKey="global.menu.entities.studentUser" /> */}
      </MenuItem>
      <MenuItem icon="asterisk" to="/bus">
        <Translate contentKey="global.menu.entities.bus" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/request-tracker">
        <Translate contentKey="global.menu.entities.requestTracker" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
