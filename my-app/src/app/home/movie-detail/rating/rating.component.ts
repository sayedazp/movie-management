import { Component, OnInit, Input, Output, EventEmitter, ViewEncapsulation } from '@angular/core';
import { MatButtonModule, MatIconAnchor, MatIconButton } from '@angular/material/button';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { MatInputModule} from '@angular/material/input';
import {MatTooltipModule} from '@angular/material/tooltip';

@Component({
  selector: 'app-rating',
  standalone: true,
  imports: [CommonModule , MatTooltipModule,MatIconModule, MatButtonModule, MatInputModule,],
  templateUrl: './rating.component.html',
  styleUrl: './rating.component.scss'
})
export class RatingComponent {
  @Input('rating') rating: number = 0;
  @Input('avg') avg: number = 0;
  @Input('starCount') starCount: number = 5;
  @Input('color') color: string = 'accent';
  @Output() private ratingUpdated = new EventEmitter();

  private snackBarDuration: number = 2000;
  ratingArr:number[] = [];

  constructor(private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    console.log("a "+this.starCount)
    for (let index = 0; index < this.starCount; index++) {
      this.ratingArr.push(index);
    }
  }
  onClick(rating:number) {
    this.snackBar.open('You rated ' + rating + ' / ' + this.starCount, '', {
      duration: this.snackBarDuration
    });
    this.ratingUpdated.emit(rating);
    console.log(rating);
    return false;
  }

  showIcon(index:number) {
    if (this.avg != null && this.avg >= index + 1) {
      return 'star';
    } else {
      return 'star_border';
    }
  }

}
export enum StarRatingColor {
  primary = "primary",
  accent = "accent",
  warn = "warn"
}
