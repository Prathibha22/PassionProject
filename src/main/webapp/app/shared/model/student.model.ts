import { IUser } from 'app/shared/model/user.model';
import { IBus } from 'app/shared/model/bus.model';

export interface IStudent {
  id?: number;
  fullName?: string | null;
  address?: string | null;
  grade?: number | null;
  contactNo?: string | null;
  user?: IUser | null;
  bus?: IBus | null;
}

export const defaultValue: Readonly<IStudent> = {};
