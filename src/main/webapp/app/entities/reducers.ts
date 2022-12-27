import student from 'app/entities/student/student.reducer';
import studentUser from 'app/entities/studentUser/studentUser.reducer';
import bus from 'app/entities/bus/bus.reducer';
import requestTracker from 'app/entities/request-tracker/request-tracker.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  student,
  studentUser,
  bus,
  requestTracker,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
