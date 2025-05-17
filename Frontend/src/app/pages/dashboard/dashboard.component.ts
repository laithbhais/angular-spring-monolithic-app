import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { ApiService } from '../../core/services/api.service';
import { UserInfo } from '../../data/UserInfo';
import { ToastrService } from 'ngx-toastr';
import { ZoomMeetingData, ZoomMeetingRequest, ZoomMeetingsData } from '../../data/ZoomMeeting';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {

  userInfo!: UserInfo;
  isView: boolean = false;
  meetingDataReq: ZoomMeetingRequest = new ZoomMeetingRequest();
  zoomMeetingData: ZoomMeetingData | null = null;
  meetings: ZoomMeetingsData[] = [];

  constructor(
    private apiService: ApiService,
    public authService: AuthService,
    private toastr: ToastrService,
  ) { }

  ngOnInit() {
    this.getUserProfile();
  }

  private getUserProfile() {
    this.apiService.getUserProfile().subscribe(
      (res) => {
        this.userInfo = res;
      }
    )
  }

  newMeeting() {
    if(this.isView) {
      this.isView = false;
    } else {
      this.apiService.createMeeting(this.meetingDataReq).subscribe(
      (res) => {
        this.zoomMeetingData = res;
        this.toastr.success("Successfully");
      },
      (err) => {
        this.toastr.success("Faild", err.error);
      },
    )
    }
  }

  viewMeetings() {
    this.isView = true;
    this.zoomMeetingData = null;
    this.apiService.listMeetings().subscribe(
      (res) => {
        this.meetings = res.meetings;
      }
    )
  }

}
