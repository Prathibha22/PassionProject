import student from 'app/entities/student/student.reducer';
import studentUser from 'app/entities/studentUser/studentUser.reducer';
import bus from 'app/entities/bus/bus.reducer';
import requestTracker from 'app/entities/request-tracker/request-tracker.reducer';
import requestTrackerUser from 'app/entities/request-trackerUser/request-trackerUser.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  student,
  studentUser,
  bus,
  requestTracker,
  requestTrackerUser,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
