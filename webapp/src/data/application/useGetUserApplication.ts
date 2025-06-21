import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { OwnApplication } from "@/types/application/OwnApplication";

export const useGetUserApplication = (id: string, execute: boolean = true) => {
  const { api } = useAxiosPrivate();

  const { data, isLoading, isError } = useQuery({
    retry: 0,
    queryKey: ["userApplication", id],
    queryFn: async () => {
      try {
        const response = await api.get<OwnApplication>(
          `/locals/${id}/applications/me`
        );
        return response.data;
      } catch (error) {
        return Promise.reject(error);
      }
    },
    enabled: execute,
  });

  return { application: data, isLoading, isError };
};
