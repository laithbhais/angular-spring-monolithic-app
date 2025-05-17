export class ZoomMeetingRequest {
    topic: string = "";
}

export interface ZoomMeetingData {
    topic: string;
    start_url: string;
    password: string;
    status: string;
}

export interface ZoomMeetingsData {
    topic: string;
    join_url: string;
}