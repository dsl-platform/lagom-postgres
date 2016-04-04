export interface Wonder {
  URI: string;
  englishName: string;
  wonderType: string;
  nativeNames: string[];
  description: string;
  ordinal: number;
  imageInfo: ImageInfo;
  totalRatings: number;
  averageRating: number;
}

export interface ImageInfo {
  imageLink: string;
  doubleWidth: boolean;
  doubleHeight: boolean;
}

export interface Comment {
  topic: string;
  user: string;
  body: string;
  rating: number;
  QueuedAt: Date;
}
