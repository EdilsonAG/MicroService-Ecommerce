using { app } from '../db/user';

// bloqueia automaticamente quem não tem token
service userService @(requires: 'authenticated-user') {
  entity User @(restrict: [{ grant: 'CREATE',to: 'any' }, { grant: ['UPDATE','DELETE', 'READ'], to: 'admin' }]) as projection on app.User;
}