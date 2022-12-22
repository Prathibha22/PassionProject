import dayjs from 'dayjs';
import { IStudent } from 'app/shared/model/student.model';
import { RequestType } from 'app/shared/model/enumerations/request-type.model';

export interface IRequestTracker {
  id?: number;
  date?: string | null;
  requestType?: RequestType | null;
  description?: string | null;
  student?: IStudent | null;
}

export const defaultValue: Readonly<IRequestTracker> = {};
