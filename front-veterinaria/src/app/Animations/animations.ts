import { trigger, state, style, transition, animate } from '@angular/animations';

export const backdropAnimation = trigger('backdrop', [
  state('open', style({ display: 'block' })),
  state('closed', style({ display: 'none' })),
]);

export const modalAnimation = trigger('modal', [
  state('open', style({ display: 'block' })),
  state('closed', style({ display: 'none' })),
  transition('closed => open', [
    style({ opacity: 0 }),
    animate('0.3s', style({ opacity: 1 })),
  ]),
  transition('open => closed', [
    animate('0.3s', style({ opacity: 0 })),
  ]),
]);
