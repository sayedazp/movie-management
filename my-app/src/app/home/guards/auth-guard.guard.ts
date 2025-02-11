import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { inject } from '@angular/core';
import { catchError, map, switchMap, take } from 'rxjs';

export const authGuardGuard: CanActivateFn = (route, state) => {
  const authService:AuthService = inject(AuthService);
  const router: Router = inject(Router);
  return authService.isAuthenticated().pipe(
    take(1),
      map((isAuthenticated) => {
        if (isAuthenticated) {
          return true; 
        } else {
          router.navigateByUrl('/login');
          return false; 
        }
      }),
      catchError((err) => {
        router.navigateByUrl('/login');
        return [false]; 
      })
    );
};
