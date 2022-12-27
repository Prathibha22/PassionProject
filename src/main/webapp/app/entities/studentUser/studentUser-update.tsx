// import React, { useState, useEffect } from 'react';
// import { Link, useNavigate, useParams } from 'react-router-dom';
// import { Button, Row, Col, FormText } from 'reactstrap';
// import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
//
// import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
// import { mapIdList } from 'app/shared/util/entity-utils';
// import { useAppDispatch, useAppSelector } from 'app/config/store';
//
// import { IUser } from 'app/shared/model/user.model';
// import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
// import { IBus } from 'app/shared/model/bus.model';
// import { getEntities as getBuses } from 'app/entities/bus/bus.reducer';
// import { IStudent } from 'app/shared/model/student.model';
// import { getEntity, updateEntity, createEntity, reset } from './studentUser.reducer';
//
// export const StudentUpdate = () => {
//   const dispatch = useAppDispatch();
//
//   const navigate = useNavigate();
//
//   const { id } = useParams<'id'>();
//   const isNew = id === undefined;
//
//   const users = useAppSelector(state => state.userManagement.users);
//   const buses = useAppSelector(state => state.bus.entities);
//   const studentEntity = useAppSelector(state => state.student.entity);
//   const loading = useAppSelector(state => state.student.loading);
//   const updating = useAppSelector(state => state.student.updating);
//   const updateSuccess = useAppSelector(state => state.student.updateSuccess);
//
//   const handleClose = () => {
//     navigate('/student');
//   };
//
//   useEffect(() => {
//     if (isNew) {
//       dispatch(reset());
//     } else {
//       dispatch(getEntity(id));
//     }
//
//     dispatch(getUsers({}));
//     dispatch(getBuses({}));
//   }, []);
//
//   useEffect(() => {
//     if (updateSuccess) {
//       handleClose();
//     }
//   }, [updateSuccess]);
//
//   const saveEntity = values => {
//     const entity = {
//       ...studentEntity,
//       ...values,
//       user: users.find(it => it.id.toString() === values.user.toString()),
//       bus: buses.find(it => it.id.toString() === values.bus.toString()),
//     };
//
//     if (isNew) {
//       dispatch(createEntity(entity));
//     } else {
//       dispatch(updateEntity(entity));
//     }
//   };
//
//   const defaultValues = () =>
//     isNew
//       ? {}
//       : {
//           ...studentEntity,
//           user: studentEntity?.user?.id,
//           bus: studentEntity?.bus?.id,
//         };
//
//   return (
//     <div>
//       <Row className="justify-content-center">
//         <Col md="8">
//           <h2 id="noteToSchoolApp.student.home.createOrEditLabel" data-cy="StudentCreateUpdateHeading">
//             <Translate contentKey="noteToSchoolApp.student.home.createOrEditLabel">Create or edit a Student</Translate>
//           </h2>
//         </Col>
//       </Row>
//       <Row className="justify-content-center">
//         <Col md="8">
//           {loading ? (
//             <p>Loading...</p>
//           ) : (
//             <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
//               {!isNew ? (
//                 <ValidatedField
//                   name="id"
//                   required
//                   readOnly
//                   id="student-id"
//                   label={translate('global.field.id')}
//                   validate={{ required: true }}
//                 />
//               ) : null}
//               <ValidatedField
//                 label={translate('noteToSchoolApp.student.fullName')}
//                 id="student-fullName"
//                 name="fullName"
//                 data-cy="fullName"
//                 type="text"
//               />
//               <ValidatedField
//                 label={translate('noteToSchoolApp.student.address')}
//                 id="student-address"
//                 name="address"
//                 data-cy="address"
//                 type="text"
//               />
//               <ValidatedField
//                 label={translate('noteToSchoolApp.student.grade')}
//                 id="student-grade"
//                 name="grade"
//                 data-cy="grade"
//                 type="text"
//               />
//               <ValidatedField
//                 label={translate('noteToSchoolApp.student.contactNo')}
//                 id="student-contactNo"
//                 name="contactNo"
//                 data-cy="contactNo"
//                 type="text"
//               />
//               <ValidatedField id="student-user" name="user" data-cy="user" label={translate('noteToSchoolApp.student.user')} type="select">
//                 <option value="" key="0" />
//                 {users
//                   ? users.map(otherEntity => (
//                       <option value={otherEntity.id} key={otherEntity.id}>
//                         {otherEntity.login}
//                       </option>
//                     ))
//                   : null}
//               </ValidatedField>
//               <ValidatedField id="student-bus" name="bus" data-cy="bus" label={translate('noteToSchoolApp.student.bus')} type="select">
//                 <option value="" key="0" />
//                 {buses
//                   ? buses.map(otherEntity => (
//                       <option value={otherEntity.id} key={otherEntity.id}>
//                         {otherEntity.name}
//                       </option>
//                     ))
//                   : null}
//               </ValidatedField>
//               <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/student" replace color="info">
//                 <FontAwesomeIcon icon="arrow-left" />
//                 &nbsp;
//                 <span className="d-none d-md-inline">
//                   <Translate contentKey="entity.action.back">Back</Translate>
//                 </span>
//               </Button>
//               &nbsp;
//               <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
//                 <FontAwesomeIcon icon="save" />
//                 &nbsp;
//                 <Translate contentKey="entity.action.save">Save</Translate>
//               </Button>
//             </ValidatedForm>
//           )}
//         </Col>
//       </Row>
//     </div>
//   );
// };
//
// export default StudentUpdate;
